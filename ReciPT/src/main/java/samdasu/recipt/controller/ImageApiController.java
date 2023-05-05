package samdasu.recipt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import samdasu.recipt.service.ImageFileService;
import samdasu.recipt.service.UserService;

/**
 * 이미지 파일 저장 테스트
 * - 이미지 파일 저장은 "리뷰 작성 & 레시피 등록에서 사용할 것"
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ImageApiController {
    private final ImageFileService imageFileService;
    private final UserService userService;
//    private final FileStore fileStore;

//    @GetMapping("/image/new")
//    public String newImage(@ModelAttribute ImageFileResponseDto imageFileResponseDto) {
//        return "item-form";
//    }

//    @PostMapping("/image/new")
//    public String saveItem(@ModelAttribute ImageFileRequestDto form, RedirectAttributes redirectAttributes) throws IOException {
//        ImageFile attachFile = fileStore.storeFile(form.getAttachFile());
//        List<ImageFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());
//
//        //데이터베이스에 저장
//        ImageFileResponseDto imageFileResponseDto = ImageFileResponseDto.createImageFileResponseDto(form.getItemName(), attachFile, storeImageFiles);
//        ImageFile saveImage = imageFileService.find(imageFileResponseDto.getImageId());
//        imageFileService.saveImages(saveImage);
//
//        redirectAttributes.addAttribute("itemId", item.getId());
//
//        return "redirect:/items/{itemId}";
//    }

//    @PostMapping("/image/new")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Long create(ReviewFileVO reviewFileVO) throws Exception {
//
//        User user = userService.findOne(
//                Long.parseLong(reviewFileVO.getUserId()));
//
//        ReviewRequestDto requestDto = ReviewRequestDto.createReviewRequestDto(user.getUsername(), reviewFileVO.getTitle(), reviewFileVO.getComment(), reviewFileVO.getRatingScore());
//
//        return imageFileService.create(requestDto, reviewFileVO.getFiles());
//    }
}
