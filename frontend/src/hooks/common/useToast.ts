import { useEffect, useRef, useState } from 'react';

const useToast = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [isAnimating, setIsAnimating] = useState(true);
  const toastTimer = useRef<NodeJS.Timeout>();

  const showToast = () => {
    setIsOpen(true);
  };

  useEffect(() => {
    setTimeout(() => setIsAnimating(false), 2000);
    if (!isAnimating) {
      setTimeout(() => clearTimeout(toastTimer.current), 500);
    }
  }, [isAnimating]);

  return { isAnimating, isOpen, showToast };
};

export default useToast;
