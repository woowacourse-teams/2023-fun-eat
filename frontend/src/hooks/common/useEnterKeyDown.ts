import type { KeyboardEventHandler } from 'react';
import { useRef } from 'react';

const useEnterKeyDown = () => {
  const inputRef = useRef<HTMLInputElement | null>(null);
  const labelRef = useRef<HTMLLabelElement | null>(null);

  const handleKeydown: KeyboardEventHandler<HTMLElement> = (e) => {
    if (e.keyCode === 13) {
      e.preventDefault();
      inputRef.current?.click();
    }
  };

  return { inputRef, labelRef, handleKeydown };
};

export default useEnterKeyDown;
