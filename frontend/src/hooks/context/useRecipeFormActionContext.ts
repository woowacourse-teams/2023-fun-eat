import { useContext } from 'react';

import { RecipeFormActionContext } from '@/contexts/RecipeFormContext';

const useRecipeFormActionContext = () => {
  const recipeFormAction = useContext(RecipeFormActionContext);
  if (recipeFormAction === null || recipeFormAction === undefined) {
    throw new Error('useRecipeFormActionContext는 RecipeFormProvider 안에서 사용해야 합니다.');
  }

  return recipeFormAction;
};

export default useRecipeFormActionContext;
