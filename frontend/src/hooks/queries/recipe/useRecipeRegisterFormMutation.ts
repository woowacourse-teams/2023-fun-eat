import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { recipeApi } from '@/apis';
import type { RecipeRequest } from '@/types/recipe';

const headers = { 'Content-Type': 'application/json' };

const useRecipeRegisterFormMutation = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: (data: RecipeRequest) => recipeApi.post({ credentials: true }, headers, data),
    onSuccess: (response) => {
      const location = response.headers.get('Location');
      if (!location) return;
      navigate(location.replace('/api', ''));
    },
  });
};

export default useRecipeRegisterFormMutation;
