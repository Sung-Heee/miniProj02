package com.example.miniproj02.board;

import com.example.miniproj02.code.CodeService;
import com.example.miniproj02.entity.BoardFileVO;
import com.example.miniproj02.entity.BoardImageFileVO;
import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.page.PageRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;
    @Autowired
    CodeService codeService;
    @Autowired
    ServletContext application;


    // pageNO => 1 이어야하는데 만약 음수면 pageRequestVO, bindingResult 여기서 처리
    // size : 10 ~ 100 이때 만약 -10이라고 주면 pageRequestVO 이걸 가지고 bindingResult 여기서 에러 값 설정이 돼서 다시 정리해줌 (pageRequestVO = PageRequestVO.builder().build();)
    @RequestMapping("list")
    // 값에 문제가 있으면 bindingResult에 에러에 관한 걸 설정
    public String list(@Valid PageRequestVO pageRequestVO, BindingResult bindingResult, Model model) throws ServletException, IOException {

        if (bindingResult.hasErrors()) {
            pageRequestVO = pageRequestVO.builder().build();
        }

        model.addAttribute("pageResponseVO", boardService.getList(pageRequestVO));
        model.addAttribute("sizes", codeService.getList());

        return "board/list";
    }

    @RequestMapping("insertForm")
    public String insertForm(Model model) {
        // 게시물 토큰을 생성하여 model에 저장
        model.addAttribute("board_token", boardService.getBoardToken());
        return "board/insertForm";
    }

    @RequestMapping("insert")
    @ResponseBody
    public Map<String , Object> insert(BoardVO boardVO) {
        Map<String, Object> map = new HashMap<>();

        int updated = boardService.insert(boardVO);

        if (updated == 1) {
            map.put("status", 0);
            map.put("statusMessage", "글이 성공적으로 등록되었습니다.");
        } else  {
            map.put("status", -1);
            map.put("statusMessage", "글 등록에 실패하였습니다.");
        }

        return map;
    }

    @RequestMapping("detail")
    public String detail(Model model, BoardVO boardVO) {
        model.addAttribute("board", boardService.detail(boardVO));
        return "board/detail";
    }

    @RequestMapping("checkPwd")
    @ResponseBody
    public Map<String, Object> checkPwd(@RequestBody BoardVO boardVO) {
        Map<String, Object> map = new HashMap<>();

        if (boardService.checkPwd(boardVO)) {
            map.put("status", 0);
        } else {
            map.put("status", -1);
            map.put("statusMessage", "비밀번호가 틀렸습니다.");
        }

        return map;
    }

    @RequestMapping("updateForm")
    public String updateForm(Model model, BoardVO boardVO) {
        model.addAttribute("board", boardService.detail(boardVO));
        return "board/updateForm";
    }

    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> update(@RequestBody BoardVO boardVO) {
        Map<String, Object> map = new HashMap<>();

        int updated = boardService.update(boardVO);

        System.out.println("updated = " + updated);
        
        if (updated == 1) {
            map.put("status", 0);
            map.put("statusMessage", "수정되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "수정에 실패하였습니다.");
        }
        return map;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody BoardVO boardVO) {
        Map<String, Object> map = new HashMap<>();

        int updated = boardService.delete(boardVO);

        if (updated == 1) {
            map.put("status", 0);
            map.put("statusMessage", "삭제되었습니다.");
        } else {
            map.put("status", -1);
            map.put("statusMessage", "삭제에 실패하였습니다.");
        }

        return map;
    }

    @RequestMapping("fileDownload/{board_file_no}")
    public void downloadFile(@PathVariable("board_file_no") int board_file_no, HttpServletResponse response) throws Exception{
        // 파일의 내용을 클라이언트에게 전송해야 하므로
        // 파일의 내용을 OutputStream에 작성하여 클라이언트로 보낸다.
        OutputStream out = response.getOutputStream();

        // 파일 번호를 통해 파일 가져오기
        BoardFileVO boardFileVO = boardService.getBoardFile(board_file_no);

        // 만약 파일이 없으면 404
        if (boardFileVO == null) {
            response.setStatus(404);
        } else {
            // 첨부파일명 가져오기
            String originName = boardFileVO.getOriginal_filename();
            // 가져온 파일 이름을 UTF-8 형식으로 URL 인코딩
            // URL 인코딩은 URL에 사용할 수 없는 문자를 특수 문자로 변환 -> URL 유효성 유지
            originName = URLEncoder.encode(originName, "UTF-8");

            // 다운로드 할 때 헤더 설정
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Content-disposition", "attachment; fileName=" + originName);        }
            response.setContentLength((int)boardFileVO.getSize());
            response.setContentType(boardFileVO.getContent_type());

            // 파일을 바이너리로 바꿔서 담아 놓고 responseOutputStream에 담아서 보낸다.
            FileInputStream input = new FileInputStream(new File(boardFileVO.getReal_filename()));

            // OutputStream에 8K씩 전달
            byte[] buffer = new byte[1024*8];

            // 파일 끝까지 읽으면 반복문 종료
            while (true) {
                // 파일에서 버퍼 크기만큼 데이터를 읽는다.
                // 읽은 바이트 수를 count에 저장
                int count = input.read(buffer);
                if (count < 0)
                    break;

                // 읽은 데이터를 클라이언트로 전송
                // 0 ~ count 만큼 전송
                out.write(buffer, 0, count);
            }
            input.close();
            out.close();

    }

    // 게시물 내용에 추가된 이미지 파일 다운로드
    @RequestMapping("image/{board_image_file_id}")
    public void image(@PathVariable("board_image_file_id") String board_image_file_id, HttpServletResponse response) throws Exception {
        OutputStream out = response.getOutputStream();

        BoardImageFileVO boardImageFileVO = boardService.getBoardImageFile(board_image_file_id);

        if (boardImageFileVO == null) {
            response.setStatus(404);
        } else {

            String originName = boardImageFileVO.getOriginal_filename();
            originName = URLEncoder.encode(originName, "UTF-8");
            //다운로드 할 때 헤더 설정
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Content-disposition", "attachment; fileName=" + originName);
            response.setContentLength((int) boardImageFileVO.getSize());
            response.setContentType(boardImageFileVO.getContent_type());

            //파일을 바이너리로 바꿔서 담아 놓고 responseOutputStream에 담아서 보낸다.
            FileInputStream input = new FileInputStream(new File(boardImageFileVO.getReal_filename()));

            //outputStream에 8k씩 전달
            byte[] buffer = new byte[1024 * 8];

            while (true) {
                int count = input.read(buffer);
                if (count < 0) break;
                out.write(buffer, 0, count);
            }
            input.close();
            out.close();
        }
    }

    @RequestMapping("boardImageUpload")
    @ResponseBody
    public Object boardImageUpload(BoardImageFileVO boardImageFileVO) throws ServletException, IOException {
        // ckeditor는 이미지 업로드 후 이미지 표시하기 위해 uploaded 와 url을 json 형식으로 받아야 함
        // ckeditor 에서 파일을 보낼 때 upload : [파일] 형식으로 해서 넘어옴, upload라는 키 이용하여 파일을 저장 한다
        MultipartFile file = boardImageFileVO.getUpload();
        String board_token = boardImageFileVO.getBoard_token();

        System.out.println("board_token = " + board_token);

        //이미지 첨부 파일을 저장한다
        String board_image_file_id = boardService.boardImageFileUpload(board_token, file);

        // 이미지를 현재 경로와 연관된 파일에 저장하기 위해 현재 경로를 알아냄
        String uploadPath = application.getContextPath() + "/board/image/" + board_image_file_id;
        log.info("uploadPath = {}", uploadPath);

        Map<String, Object> result = new HashMap<>();
        result.put("uploaded" , true); // 업로드 완료
        result.put("url", uploadPath); // 업로드 파일 경로

        return result;
    }

}
