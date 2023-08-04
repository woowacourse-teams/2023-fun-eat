import type { KeyboardEventHandler } from 'react';
import { useRef } from 'react';

const useEnterKeyDown = () => {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const handleKeydown: KeyboardEventHandler<HTMLLabelElement> = (e) => {
    if (e.keyCode === 13) {
      e.preventDefault();
      inputRef.current?.click();
    }
  };

  return { inputRef, handleKeydown };
};

export default useEnterKeyDown;
