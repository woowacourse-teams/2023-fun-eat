import type { PropsWithChildren } from 'react';
import { createContext, useState } from 'react';

import type { RecipeRequest } from '@/types/recipe';

type RecipeValue = Omit<RecipeRequest, 'images'>;

interface RecipeFormActionParams {
  target: keyof RecipeValue;
  value: string | number;
  action?: 'add' | 'remove';
}

interface RecipeFormAction {
  handleRecipeFormValue: (params: RecipeFormActionParams) => void;
  resetRecipeFormValue: () => void;
}

const initialRecipeFormValue: RecipeValue = {
  title: '',
  productIds: [],
  content: '',
};

export const RecipeFormValueContext = createContext<RecipeValue | null>(null);
export const RecipeFormActionContext = createContext<RecipeFormAction | null>(null);

const RecipeFormProvider = ({ children }: PropsWithChildren) => {
  const [recipeFormValue, setRecipeFormValue] = useState<RecipeValue>({
    title: '',
    productIds: [],
    content: '',
  });

  const handleRecipeFormValue = ({ target, value, action }: RecipeFormActionParams) => {
    setRecipeFormValue((prev) => {
      const targetValue = prev[target];

      if (Array.isArray(targetValue)) {
        if (action === 'remove') {
          return { ...prev, [target]: targetValue.filter((id) => id !== value) };
        }

        if (targetValue.includes(Number(value))) {
          return prev;
        }

        return { ...prev, [target]: [...targetValue, value] };
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
