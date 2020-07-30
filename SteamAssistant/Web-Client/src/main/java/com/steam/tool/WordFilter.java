package com.steam.tool;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.util.List;

// 实现对文本的分词（移除停用词）
public class WordFilter {
    public WordFilter() {
    }

    public static  List<Word> automaticSelection(String title) {
        List<Word> list = WordSegmenter.seg(title);
        return list;
    }
}
