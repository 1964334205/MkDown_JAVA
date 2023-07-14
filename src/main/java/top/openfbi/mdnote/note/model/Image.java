package top.openfbi.mdnote.note.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 图片实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Image {

    /**
     * 文件md5值
     */
    private String md5;


    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件追加数字
     */
    private int suffix;

    /**
     * 文件url
     */
    private String fileUrl;

    /**
     * 文件扩展名
     */
    private String extension;


}
