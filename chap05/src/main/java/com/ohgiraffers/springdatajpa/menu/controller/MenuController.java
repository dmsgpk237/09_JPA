package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.PageNation;
import com.ohgiraffers.springdatajpa.common.PagingButtonInfo;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 메뉴 단일 조회 기능
    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable("menuCode") int menuCode, Model model) {
        log.info("menuCode = {}", menuCode);

        MenuDTO menu =  menuService.findMenuByCode(menuCode);
        model.addAttribute("menu", menu);
        return "menu/detail";

    }
    /* 페이징 처리 전 */
//    @GetMapping("/list")
//    public String findMenuList(Model model){
//
//        List<MenuDTO> menuList = menuService.findMenuList();
//
//        model.addAttribute("menuList", menuList);
//
//        return "menu/list";
//    }

    @GetMapping("/list")
    public String findAllMenus(@PageableDefault Pageable pageable, Model model){
        log.info("pageable = {}", pageable);

        Page<MenuDTO> menuList = menuService.findAllMenus(pageable);
        // 여러개를 담아오는 데 페이징 처리가 된 친구, 메뉴 DTO를 페이징 처리하는 걸로 감싸주는 것

        log.info("조회한 내용 목록 : {}", menuList.getContent());
        log.info("총 페이지 수 : {}", menuList.getTotalPages());
        log.info("총 메뉴 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표시 될 요소 수 : {}", menuList.getSize());
        log.info("해당 페이지에 실제 요소 수 : {}", menuList.getNumberOfElements());
        log.info("첫 페이지 여부 : {}", menuList.isFirst());
        log.info("마지막 페이지 여부 : {}", menuList.isLast());
        log.info("정렬 방식 : {}", menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

        PagingButtonInfo paging = PageNation.getPagingButtonInfo(menuList);
        model.addAttribute("paging", paging);
        model.addAttribute("menuList", menuList);

        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethodPage() {}

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {
        log.info("menuPrice =============== {}", menuPrice);

        List<MenuDTO> menuList =  menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList", menuList);
        model.addAttribute("menuPrice", menuPrice);

        return "menu/searchResult";

    }

    @GetMapping("/regist")
    public void registPage() {}

    @GetMapping(value = "category", produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<CategoryDTO> categoryPage() {

        List<CategoryDTO> categoryList = menuService.findAllCategory();
        log.info("categoryList ================= {}", categoryList);


        return categoryList;

    }

    @PostMapping("/regist")
    public String registNewMenu(@ModelAttribute MenuDTO newMenu) {

        log.info("newMenu =================== > {}", newMenu);

        menuService.registNewMenu(newMenu);

        return "redirect:/menu/list";

    }

    @GetMapping("/modify/{menuCode}")
    public String modifyPage(@PathVariable int menuCode, Model model) {

        log.info("menuCode ==== {}", menuCode);

        // 메뉴 코드로 메뉴 조회해오는 기능
        MenuDTO menu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", menu);

        return "menu/modify";
    }

    @PostMapping("/modify")
    // ModelAttribute 생략 가능
    public String modifyMenu(MenuDTO modifyMenu) {

        log.info("modifyMenu ========= {}", modifyMenu);

        menuService.modifyMenu(modifyMenu);

        return "redirect:/menu/modify/" + modifyMenu.getMenuCode();
    }

    @GetMapping("/delete")
    public void deletePage() {}

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam Integer menuCode) {
        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }
}