package vn.huan.shoppingcart.ShoppingCart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.huan.shoppingcart.ShoppingCart.dto.ImageDto;
import vn.huan.shoppingcart.ShoppingCart.exceptions.ResourceNotFoundException;
import vn.huan.shoppingcart.ShoppingCart.model.Image;
import vn.huan.shoppingcart.ShoppingCart.response.ApiResponse;
import vn.huan.shoppingcart.ShoppingCart.service.image.IimageService;

import javax.print.attribute.standard.Media;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IimageService imageService;

@PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files,productId);
            return  ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload success!", e.getMessage()));
        }

    }
    @GetMapping("/image/download/{imageId}")
    public  ResponseEntity<Resource>  downloadImage (@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"" )
                .body(resource);
    }
    @PutMapping("/image/{imageId}/update")

    public  ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null ){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Update Succes!",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed",INTERNAL_SERVER_ERROR));
    }
    @DeleteMapping("/image/{imageId}/delete")

    public  ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null ){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Succes!",null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed",INTERNAL_SERVER_ERROR));
    }

}
