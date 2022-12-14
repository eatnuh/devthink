package com.eatnuh.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("카테고리 도메인 테스트 - DIGITAL의 이름은 가전/디지털 이다.")
    void digitalGetTitleTest() {
        String heelTitle = Category.DIGITAL.getTitle();

        assertEquals(heelTitle, "가전/디지털");
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - ROOT의 상위카테고리는 없다.")
    void rootGetParentCategoryIsEmptyTest() {
        Optional<Category> rootParentCategory = Category.ROOT.getParentCategory();

        assertTrue(rootParentCategory.isEmpty());
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - MEAT_EGG의 상위카테고리는 FOOD이다.")
    void meatEggGetParentCategoryIsFoodTest() {
        Optional<Category> meatEggParentCategory = Category.MEAT_EGG.getParentCategory();

        assertEquals(meatEggParentCategory.get(), Category.FOOD);
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - WALLET_BELT의 하위카테고리에 WALLET, BELT가 있다.")
    void walletBeltGetSubCategoriesContainsWalletAndBeltTest() {
        List<Category> walletBeltSubCategories = Category.WALLET_BELT.getChildCategories();

        assertTrue(walletBeltSubCategories.contains(Category.WALLET));
        assertTrue(walletBeltSubCategories.contains(Category.BELT));
    }

    @Test
    @DisplayName("키테고리 도메인 테스트 - FASHION_CHILDREN의 하위카테고리에 TV가 없다.")
    void fashionChildrenGetSubCategoriesNotContainsTVTest() {
        List<Category> fashionChildrenSubCategories = Category.FASHION_CHILDREN.getChildCategories();

        assertFalse(fashionChildrenSubCategories.contains(Category.TV));
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - BAG은 리프카테고리가 아니다.")
    void bagIsLeafCategoryIsFalseTest() {
        assertFalse(Category.BAG.isLeafCategory());
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - MEN_PANTS는 리프카테고리다.")
    void menPantsIsLeafCategoryIsTrueTest() {
        assertTrue(Category.MEN_PANTS.isLeafCategory());
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - 하위카테고리 변경 금지 테스트")
    void subCategoriesLisIsUnmodifiableTest() {
        assertThrows(UnsupportedOperationException.class,
                () -> Category.MEAT_EGG.getChildCategories().add(Category.BEVERAGE)
        );
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - [TV, PROJECTOR, COMPUTER]는 DIGITAL의 리프카테고리다.")
    void TVAndProjectorAndComputerAreLeafCategoryOfDigitalTest() {
        List<Category> leafCategories = Category.DIGITAL.getLeafCategories();

        assertTrue(leafCategories.contains(Category.TV));
        assertTrue(leafCategories.contains(Category.PROJECTOR));
        assertTrue(leafCategories.contains(Category.COMPUTER));
    }

    @Test
    @DisplayName("카테고리 도메인 테스트 - INSTANT는 FASHION_WOMEN의 리프카테고리가 아니다.")
    void InstantIsNotLeafCategoryOfFashionWomenTest() {
        List<Category> leafCategories = Category.FASHION_WOMEN.getLeafCategories();

        assertFalse(leafCategories.contains(Category.INSTANT));
    }

    @Test
    void print() {
        System.out.println(Category.FOOD.getParentCategory());
    }
}