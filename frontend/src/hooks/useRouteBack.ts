import { useNavigate } from 'react-router-dom';

const useRouteBack = () => {
  const navigate = useNavigate();

  const routeBack = () => {
    navigate(-1);
  };

  return routeBack;
};

export default useRouteBack;
