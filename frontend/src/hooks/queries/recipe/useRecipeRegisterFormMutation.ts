import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { recipeApi } from '@/apis';

const useRecipeRegisterFormMutation = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: (data: FormData) => recipeApi.postData({ credentials: true }, data),
    onSuccess: (response) => {
      const location = response.headers.get('Location');
      if (!location) return;
      navigate(location);
    },
  });
};

export default useRecipeRegisterFormMutation;
