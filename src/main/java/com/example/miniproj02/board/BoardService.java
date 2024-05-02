package com.example.miniproj02.board;

import com.example.miniproj02.entity.BoardFileVO;
import com.example.miniproj02.entity.BoardVO;
import com.example.miniproj02.page.PageRequestVO;
import com.example.miniproj02.page.PageResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    @Autowired
    BoardFileMapper boardFileMapper;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String CURR_IMAGE_REPO_PATH = "/Users/hee/upload";

    // 날짜 서식 생성
    private final SimpleDateFormat date_format = new SimpleDateFormat(File.separator + "MM" + File.separator + "dd");

    public PageResponseVO<BoardVO> getList(PageRequestVO pageRequestVO) {
        List<BoardVO> list = boardMapper.getList(pageRequestVO);
        int total = boardMapper.getTotalCount(pageRequestVO);

        PageResponseVO<BoardVO> pageResponseVO = PageResponseVO.<BoardVO>withAll()
                .list(list)
                .total(total)
                .size(pageRequestVO.getSize())
                .pageNo(pageRequestVO.getPageNo())
                .build();

        return pageResponseVO;
    }

    public int insert(BoardVO boardVO) {
        String password = bCryptPasswordEncoder.encode(boardVO.getBoard_pwd());
        boardVO.setBoard_pwd(password);

        // 게시물 등록시 게시물의 번호 얻기
        int result = boardMapper.insert(boardVO);

        // MultipartFile 객체를 파일에 저장하기
        BoardFileVO boardFileVO = writeFile(boardVO.getFile());
        if (boardFileVO != null) {
            // 첨부파일에 게시물의 아이디를 설정한다.
            System.out.println("boardVO = " + boardVO);
            boardFileVO.setBoard_id(boardVO.getBoard_id());

            // 파일 정보를 DB에 저장한다.
            result = boardFileMapper.insert(boardFileVO);
        }

        return result;
    }

    // MultipartFile 객체를 파일에 저장한다.
    private BoardFileVO writeFile(MultipartFile file) {
        if (file == null || file.getName() == null)
            return null;

        Calendar now = Calendar.getInstance();

        // 저장 위치를 오늘의 날짜로
        String realFolder = date_format.format(now.getTime());

        // 실제 저장 위치 생성
        File realPath = new File(CURR_IMAGE_REPO_PATH + realFolder);

        // 오늘 날짜에 대한 폴더가 없으면 생성
        if (!realPath.exists()) {
            realPath.mkdirs();
        }

        // 실제 파일명으로 사용할 이름을 생성 ? ?
        String fileNameReal = UUID.randomUUID().toString();
        File realFile = new File(realPath, fileNameReal);

        // 파일을 실제 위치에 저장
        try {
            file.transferTo(realFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 저장된 첨부파일 객체를 리턴
        return BoardFileVO.builder()
                .content_type(file.getContentType())
                .original_filename(file.getOriginalFilename())
                .real_filename(realFile.getAbsolutePath())
                .size(file.getSize())
                .build();
    }

    public BoardVO detail(BoardVO boardVO) {
        BoardVO resultVO = boardMapper.detail(boardVO);
        return resultVO;
    }

    public boolean checkPwd(BoardVO boardVO) {
        BoardVO resultVO = boardMapper.detail(boardVO);
        return bCryptPasswordEncoder.matches(boardVO.getInput_pwd(), resultVO.getBoard_pwd());
    }

    public int update(BoardVO boardVO) {
        return boardMapper.update(boardVO);
    }

    public int delete(BoardVO boardVO) {
        return boardMapper.delete(boardVO);
    }
}
