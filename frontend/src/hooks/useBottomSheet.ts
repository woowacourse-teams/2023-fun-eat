import { useRef } from 'react';

const useBottomSheet = () => {
  const ref = useRef<HTMLDialogElement>(null);

  const handleOpenBottomSheet = () => {
    ref.current?.showModal();
  };

  const handleCloseBottomSheet = () => {
    ref.current?.close();
  };

  return { ref, handleOpenBottomSheet, handleCloseBottomSheet };
};

export default useBottomSheet;
