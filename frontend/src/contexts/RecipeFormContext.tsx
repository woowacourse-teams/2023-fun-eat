import type { PropsWithChildren } from 'react';
import { createContext, useState } from 'react';

import type { RecipeRequest, RecipeRequestKey } from '@/types/recipe';

interface RecipeFormActionParams {
  target: RecipeRequestKey;
  value: string | number;
}

interface RecipeFormAction {
  handleRecipeFormValue: (params: RecipeFormActionParams) => void;
  resetRecipeFormValue: () => void;
}

const initialRecipeFormValue: RecipeRequest = {
  title: '',
  productIds: [],
  content: '',
};

export const RecipeFormValueContext = createContext<RecipeRequest | null>(null);
export const RecipeFormActionContext = createContext<RecipeFormAction | null>(null);

const RecipeFormProvider = ({ children }: PropsWithChildren) => {
  const [recipeFormValue, setRecipeFormValue] = useState<RecipeRequest>({
    title: '',
    productIds: [],
    content: '',
  });

  const handleRecipeFormValue = ({ target, value }: RecipeFormActionParams) => {
    setRecipeFormValue((prev) => {
      const targetValue = prev[target];
      if (Array.isArray(targetValue)) {
        return { ...prev, [target]: targetValue.filter((id) => id !== value) };
      }
      return { ...prev, [target]: value };
    });
  };

  const resetRecipeFormValue = () => {
    setRecipeFormValue(initialRecipeFormValue);
  };

  const recipeFormAction = {
    handleRecipeFormValue,
    resetRecipeFormValue,
  };

  return (
    <RecipeFormActionContext.Provider value={recipeFormAction}>
      <RecipeFormValueContext.Provider value={recipeFormValue}>{children}</RecipeFormValueContext.Provider>
    </RecipeFormActionContext.Provider>
  );
};

export default RecipeFormProvider;
