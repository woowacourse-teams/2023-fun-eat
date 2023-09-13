import type { PropsWithChildren } from 'react';
import { useState, createContext } from 'react';

import type { CategoryVariant } from '@/types/common';

const initialState = {
  food: 1,
  store: 7,
};

type CategoryIds = {
  [k in CategoryVariant]: number;
};

interface CategoryState {
  categoryIds: CategoryIds;
  selectCategory: (menuVariant: string, categoryId: number) => void;
  currentTabScroll: { [key: number]: number };
  saveCurrentTabScroll: (categoryId: number, scrollY: number) => void;
}

export const CategoryContext = createContext<CategoryState>({
  categoryIds: initialState,
  selectCategory: () => {},
  currentTabScroll: {},
  saveCurrentTabScroll: () => {},
});

const CategoryProvider = ({ children }: PropsWithChildren) => {
  const [categoryIds, setCategoryIds] = useState(initialState);
  const [currentTabScroll, setCurrentTabScroll] = useState({});

  const selectCategory = (menuVariant: string, categoryId: number) => {
    setCategoryIds((prevCategory) => ({ ...prevCategory, [menuVariant]: categoryId }));
  };

  const saveCurrentTabScroll = (categoryId: number, scrollY: number) => {
    setCurrentTabScroll((prevState) => ({ ...prevState, [categoryId]: scrollY }));
  };

  const categoryState: CategoryState = {
    categoryIds,
    currentTabScroll,
    selectCategory,
    saveCurrentTabScroll,
  };

  return <CategoryContext.Provider value={categoryState}>{children}</CategoryContext.Provider>;
};

export default CategoryProvider;
