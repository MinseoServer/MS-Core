package kr.ms.core.iridium.pattern;

import kr.ms.core.iridium.ColorApi;

import java.awt.*;
import java.util.regex.Matcher;

public class GradientPattern implements Pattern {
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<GRADIENT:([0-9A-Fa-f]{6})>(.*?)</GRADIENT:([0-9A-Fa-f]{6})>");

    public GradientPattern() {
    }

    public String process(String string) {
        String start = "";
        String end = "";
        String content = "";
        for(Matcher matcher = this.pattern.matcher(string); matcher.find(); string = string.replace(matcher.group(), ColorApi.color(content, new Color(Integer.parseInt(start, 16)), new Color(Integer.parseInt(end, 16))))) {
            start = matcher.group(1);
            end = matcher.group(3);
            content = matcher.group(2);
        }
        return string;
    }
}