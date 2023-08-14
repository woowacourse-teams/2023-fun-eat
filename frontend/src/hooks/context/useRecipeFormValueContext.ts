import { useContext } from 'react';

import { RecipeFormValueContext } from '@/contexts/RecipeFormContext';

const useRecipeFormValueContext = () => {
  const recipeFormValue = useContext(RecipeFormValueContext);

  if (recipeFormValue === null || recipeFormValue === undefined) {
    throw new Error('useRecipeFormValueContext는 RecipeFormProvider 안에서 사용해야 합니다.');
  }

  return recipeFormValue;
};

export default useRecipeFormValueContext;
