package pastry.coffeecoding.joshuablog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pastry.coffeecoding.joshuablog.core.auth.MyUserDetails;
import pastry.coffeecoding.joshuablog.dto.board.BoardRequest;
import pastry.coffeecoding.joshuablog.model.board.Board;
import pastry.coffeecoding.joshuablog.service.BoardService;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @PostMapping("/s/board/{id}/delete")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        boardService.게시글삭제(id, myUserDetails.getUser().getId());
        return "redirect:/";
    }

    // RestAPI 주소 설계 규칙에서 지원에는 복수를 붙인다. boards 정석
    @GetMapping({"/", "/board"})
    public String main(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword, // PK or UK가 아니기 때문에
            Model model
    ){
        Page<Board> boardPG = boardService.글목록보기(page, keyword); // OSIV = false: 영속 -> 비영속이된다.(세션에 연결이 안된다)
        model.addAttribute("boardPG", boardPG);
        model.addAttribute("keyword", keyword);
        return "board/main";
    }

    @GetMapping("/s/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/s/board/save") // 인증 필요
    public String save(BoardRequest.SaveInDTO saveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails) {
        boardService.글쓰기(saveInDTO, myUserDetails.getUser().getId());
        return "redirect:/";
    }

    @GetMapping( "/board/{id}")
    public String detail(@PathVariable Long id, Model model){
        Board board = boardService.게시글상세보기(id);
        model.addAttribute("board", board); // model에 담는 이유: SSR project에서는 request.setAttribute에 담아야 EL표현식 가능
        return "board/detail"; // RequestDispatcher -> request 덮어쓰기
    }
}
