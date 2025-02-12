package vn.huan.shoppingcart.ShoppingCart.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.huan.shoppingcart.ShoppingCart.dto.ImageDto;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Image;
import vn.huan.shoppingcart.ShoppingCart.model.Product;
import vn.huan.shoppingcart.ShoppingCart.repository.ImageRepository;
import vn.huan.shoppingcart.ShoppingCart.service.product.IProductService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class imageService implements IimageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No image found with id :"+id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()-> {
            throw new ResourceNotFoundException("No image found with id :"+id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> file, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file1:file){
            try{
                Image image = new Image();
                image.setFileName(file1.getOriginalFilename());
                image.setFileType(file1.getContentType());
                image.setImage(new SerialBlob(file1.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl =buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage= imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            } catch (IOException | SQLException e){
                throw  new RuntimeException(e.getMessage());

            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
