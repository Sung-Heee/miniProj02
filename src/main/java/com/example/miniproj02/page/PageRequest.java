package com.example.miniproj02.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int pageNo = 1;

    @Builder.Default
    @Min(value = 10)
    @Max(value = 100)
    @Positive
    private int size = 10;

    private String link;

    private String searchKey;
    private String searchName;
    private String searchPhone;

    public int getSkip(){
        return (pageNo - 1) * 10;
    }

    public String getLink() {
        if(link == null){
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.pageNo);
            builder.append("&size=" + this.size);

            // 검색어가 존재할 경우
            if (this.searchKey != null && this.searchKey.length() != 0) {
                try {
                    builder.append("&searchKey=" + URLEncoder.encode(this.searchKey,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // 검색 이름이 존재할 경우
            if (this.searchName != null && this.searchName.length() != 0) {
                try {
                    builder.append("&searchName=" + URLEncoder.encode(this.searchName,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // 검색 전화번호가 존재할 경우
            if (this.searchPhone != null && this.searchPhone.length() != 0) {
                try {
                    builder.append("&searchPhone=" + URLEncoder.encode(this.searchPhone,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            link = builder.toString();
        }
        return link;
    }
}
