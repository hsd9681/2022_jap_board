package com.hsd.exam.jpaBoard.article.controller;

import com.hsd.exam.jpaBoard.User.dao.UserRepository;
import com.hsd.exam.jpaBoard.User.domain.User;
import com.hsd.exam.jpaBoard.article.dao.ArticleRepository;
import com.hsd.exam.jpaBoard.article.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usr/article")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("list")
    @ResponseBody
    public List<Article> showList() {
        return articleRepository.findAll();
    }

    @RequestMapping("detail")
    @ResponseBody
    public Article showDetail(long id) {
        Optional<Article> article = articleRepository.findById(id);//게시물 n번쨰 조회기능,id 매개변수에 nL이 들어옵니다.
        return article.get();
    }
    @RequestMapping("doModify")
    @ResponseBody
    public Article doModify(long id, String title, String body) {
        Article article = articleRepository.findById(id).get();

        if ( title != null ) {
            article.setTitle(title);
        }

        if ( body != null ) {
            article.setBody(body);
        }

        article.setUpdateDate(LocalDateTime.now());

        articleRepository.save(article);

        return article;
    }

    @RequestMapping("doDelete")
    @ResponseBody
    public String doDelete(long id) {
        if ( articleRepository.existsById(id) == false ) {
            return "%d번 게시물은 이미 삭제되었거나 존재하지 않습니다.".formatted(id);
        }

        articleRepository.deleteById(id);
        return "%d번 게시물이 삭제되었습니다.".formatted(id);
    }
    @RequestMapping("doWrite")
    @ResponseBody
    public String doWrite(String title, String body){
        if (title == null || title.trim().length() == 0) {
            return "제목을 입력해 주세요.";
        }

        if(body == null || body.trim().length() == 0){
            return "내용을 입력해 주세요.";
        }

        body = body.trim();

        Article article = new Article();
        article.setRegDate(LocalDateTime.now());
        article.setUpdateDate(LocalDateTime.now());
        article.setTitle(title);
        article.setBody(body);
        User user = userRepository.findById(3L).get();

        article.setUser(user);
        articleRepository.save(article);

        return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
    }
    @RequestMapping("doJoin")
    @ResponseBody
    public String doJoin(String name, String email, String password) {

        if(name == null || name.trim().length() == 0){
            return "내용을 입력해 주세요.";
        }
        name = name.trim();
        if(email == null || email.trim().length() == 0){
            return "내용을 입력해 주세요.";
        }
        email = email.trim();
        boolean existsByEmail = userRepository.existsByEmail(email);
        if ( existsByEmail ) {
            return "입력하신 이메일(%s)은 이미 사용중입니다.".formatted(email);
        }
        if(password == null || password.trim().length() == 0){
            return "내용을 입력해 주세요.";
        }
        User user = new User();
        user.setRegDate(LocalDateTime.now());
        user.setUpdateDate(LocalDateTime.now());
        user.setName(name);
        password = password.trim();
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "%d번 회원이 생성되었습니다.".formatted(user.getId());
    }

    @RequestMapping("doLogin")
    @ResponseBody
    public String doLogin(String email, String password) {
        if (email == null || email.trim().length() == 0) {
            return "이메일을 입력해주세요.";
        }

        email = email.trim();
        //User user = userRepository.findByEmail(email).orElse(null); // 방법1
        Optional<User> user = userRepository.findByEmail(email); // 방법2
        if (user.isEmpty()) {
            return "일치하는 회원이 존재하지 않습니다.";
        }
        if (password == null || password.trim().length() == 0) {
            return "비밀번호를 입력해주세요.";
        }
        password = password.trim();
        if (user.get().getPassword().equals(password) == false) {
            return "비밀번호가 일치하지 않습니다.";
        }
        return "%s님 환영합니다.".formatted(user.get().getName());
    }

}