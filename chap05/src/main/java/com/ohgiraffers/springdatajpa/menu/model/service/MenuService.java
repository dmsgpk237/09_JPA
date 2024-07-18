package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.entity.Category;
import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
    }

    public MenuDTO findMenuByCode(int menuCode) {

        // MenuDTO -> 일반클래스 / 영속성 컨텍스트에서 관리 안 됨, 객체간 이동할 때
        // Menu -> Entity / 영속성 컨텍스트에서 관리됨, 영속성 컨텍스트에 올릴 때

        /*
        * findById 메소드는 이미 구현이 되어있다.
        * 반환값은 Optional 타입이고 Optional Type은 NPE 을 방지하기 위해 다양한 기능을 제공한다.
        * */

        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new); // 예외사항 처리

        log.info("menu ============== {}", menu);
        return modelMapper.map(menu, MenuDTO.class);
    }

    public List<MenuDTO> findMenuList() {
        List<Menu> menuList = menuRepository.findAll();
        // menuRepository.findAll(sort.by("menuPrice").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());
    }

    public Page<MenuDTO> findAllMenus(Pageable pageable) {
        // page Parameter 가 Pageable 의 number 로 넘어옴
        // 조회했을 때는 인덱스 기준이 되기 때문에 -1
        pageable = PageRequest.of( // pageRequest.of -> pageable 객체 조작
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by("menuCode").descending());
        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

//        List<Menu> menuList = null;
//
//        if (menuPrice == 0) {
//            menuList = menuRepository.findAll();
//        } else if (menuPrice > 0) {
//
//
//            menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice,
//                    Sort.by("menuPrice").descending());
//        }


        // menuPrice와 같은 금액의 메뉴
        List<Menu> menuList = null;

        if (menuPrice == 0) {
            menuList = menuRepository.findAll();
        } else if (menuPrice > 0) {


            menuList = menuRepository.findByMenuPriceEquals(menuPrice);
        }

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());

    }

//    public List<CategoryDTO> findAllCategory() {
//
//        List<Category> categoryList = categoryRepository.findAllCategory();
//    }
}
