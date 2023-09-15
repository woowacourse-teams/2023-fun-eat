import type { PropsWithChildren } from 'react';
import { useState, createContext } from 'react';

import type { CategoryVariant } from '@/types/common';

const initialState = {
  food: 1,
  store: 7,
};

export type CategoryIds = {
  [k in CategoryVariant]: number;
};

interface CategoryValue {
  categoryIds: CategoryIds;
  currentTabScroll: { [key: number]: number };
}

interface CategoryAction {
  selectCategory: (menuVariant: string, categoryId: number) => void;
  saveCurrentTabScroll: (categoryId: number, scrollY: number) => void;
}

export const CategoryValueContext = createContext<CategoryValue | null>(null);
export const CategoryActionContext = createContext<CategoryAction | null>(null);

const CategoryProvider = ({ children }: PropsWithChildren) => {
  const [categoryIds, setCategoryIds] = useState(initialState);
  const [currentTabScroll, setCurrentTabScroll] = useState({});

  const selectCategory = (menuVariant: string, categoryId: number) => {
    setCategoryIds((prevCategory) => ({ ...prevCategory, [menuVariant]: categoryId }));
  };

  const saveCurrentTabScroll = (categoryId: number, scrollY: number) => {
    setCurrentTabScroll((prevState) => ({ ...prevState, [categoryId]: scrollY }));
  };

  const categoryValue = {
    categoryIds,
    currentTabScroll,
  };

  const categoryAction = {
    selectCategory,
    saveCurrentTabScroll,
  };

  return (
    <CategoryActionContext.Provider value={categoryAction}>
      <CategoryValueContext.Provider value={categoryValue}>{children}</CategoryValueContext.Provider>
    </CategoryActionContext.Provider>
  );
};

export default CategoryProvider;
