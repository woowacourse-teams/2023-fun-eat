import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

import { logoutApi } from '@/apis';

const useLogoutMutation = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: () => logoutApi.post({ credentials: true }),
    onSuccess: (response) => {
      const location = response.headers.get('Location');
      if (!location) return;
      navigate(location);
    },
    onError: (error) => {
      if (error instanceof Error) {
        alert(error.message);
        return;
      }
      alert('로그아웃을 다시 시도해주세요.');
    },
  });
};

export default useLogoutMutation;
