import type { Category } from '@/types/common';

export const getTargetCategoryName = (categories: Category[], id: number) =>
  categories.find((category) => category.id === id)?.name;
