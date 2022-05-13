package com.sprangu.spranguback.application.utils;

import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ImageUtils {

    public static List<String> resizeToThumbnails(List<String> images) {
        return images.stream()
                .map(ImageUtils::resizeToThumbnails)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public static String resizeToThumbnails(String image) {
        if (image == null) {
            return null;
        }
        try {
            var info = image.split(",");
            var img = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(info[1])));
            var out = new ByteArrayOutputStream();
            ImageIO.write(Scalr.resize(img, 320), "jpeg", out);
            return info[0] + "," + Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            log.error("Error parsing image : " + e.getMessage());
            return null;
        }
    }
}
