import type { PropsWithChildren } from 'react';
import { useState, createContext } from 'react';

const initialState = {
  food: 1,
  store: 1,
};

interface CategoryState {
  categories: { food: number; store: number };
  selectCategory: (menuVariant: string, categoryId: number) => void;
}

export const CategoryContext = createContext<CategoryState>({
  categories: initialState,
  selectCategory: () => {},
});

const CategoryProvider = ({ children }: PropsWithChildren) => {
  const [categories, setCategories] = useState(initialState);

  const selectCategory = (menuVariant: string, categoryId: number) => {
    setCategories((prevCategory) => ({ ...prevCategory, [menuVariant]: categoryId }));
  };

  const categoryState: CategoryState = {
    categories,
    selectCategory,
  };

  return <CategoryContext.Provider value={categoryState}>{children}</CategoryContext.Provider>;
};

export default CategoryProvider;
