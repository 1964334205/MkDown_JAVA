package top.openfbi.mdnote.note.model.fe;

import lombok.Data;

@Data
public class SimplificationNote {
    /**
     * ID
     */
    private Long id;

    /**
     * 笔记标题
     */
    private String title;

    /**
     * 笔记内容
     */
    private String content;
}
