package samdasu.recipt.controller;


import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.service.ImageFileService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageFileService imageFileService;

    @RequestMapping("upload/insert")
    public String Insert() {
        return "image-upload";
    }

    @RequestMapping("upload/fileinsert")
    public String fileInsert(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception {

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        String fileUrl = "/Users/jaehyun/Documents/IdeaProjects/ReciPT/ReciPT-backend/ReciPT/src/main/resources/images/";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        ImageFile imageFile = ImageFile.createImageFile(destinationFileName, sourceFileName, fileUrl);
        imageFileService.saveImage(imageFile);
        return "redirect:/upload/insert";
    }

    @RequestMapping("upload/check")
    public String findOriName(Model model) {
        model.addAttribute("file", imageFileService.findByOriginName("변수명 창작 불가.PNG"));
        return "image-open";
    }
}
