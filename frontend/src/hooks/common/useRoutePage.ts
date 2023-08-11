import { useNavigate } from 'react-router-dom';

import { PATH } from '@/constants/path';

const useRoutePage = () => {
  const navigate = useNavigate();

  const routeBack = () => {
    navigate(-1);
  };

  const routeHome = () => {
    navigate(PATH.HOME);
  };

  return { routeBack, routeHome };
};

export default useRoutePage;
