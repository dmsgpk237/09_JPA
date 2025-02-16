package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.entity.Category;
import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.repository.MenuRepository;
import jakarta.transaction.Transactional;
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

    // 생성자 주입
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 메소드 정보 적어줌 : 메뉴 코드로 메뉴를 찾는 메소드
     * @param menuCode : 찾을 메뉴의 메뉴코드
     * @return
     */
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

    public List<CategoryDTO> findAllCategory() {

//        List<Category> categoryList = categoryRepository.findAll();
//        List<Category> categoryList = categoryRepository.findAllCategoryByJPQL();

        List<Category> categoryList = categoryRepository.findAllCategoryByNativeQuery();

        return categoryList.stream().map(category -> modelMapper
                        .map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void registNewMenu(MenuDTO newMenu) {

        Menu menu = modelMapper.map(newMenu, Menu.class);

        // Builder 적용
//        Menu menu = new Menu().builder()
//                .menuName(newMenu.getMenuName())
//                .menuPrice(newMenu.getMenuPrice())
//                .menuCode(newMenu.getMenuCode())
//                .categoryCode(newMenu.getCategoryCode())
//                .orderableStatus(newMenu.getOrderableStatus())
//                .build();

        menuRepository.save(menu);

    }

    // 엔티티만 영속이 가능하기 때문에 DTO인 modifyMenu는 영속화가 불가능하다.
    @Transactional
    public void modifyMenu(MenuDTO modifyMenu /*변경할 이름, 변경할 대상의 코드*/ ) {

        // modifyMenu -> 비영속
        // 영속

        Menu foundMenu = menuRepository.findById(modifyMenu.getMenuCode()).
                orElseThrow(() -> new IllegalArgumentException("Menu not found"));

//        foundMenu.toBuilder().menuName(modifyMenu.getMenuName()).build();
        foundMenu.setMenuName(modifyMenu.getMenuName());
        log.info("foundMenu ============> {}", foundMenu);

    }

    @Transactional
    public void deleteMenu(Integer menuCode) {
        menuRepository.deleteById(menuCode);
    }
}
