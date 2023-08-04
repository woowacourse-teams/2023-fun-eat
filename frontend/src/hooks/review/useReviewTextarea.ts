import type { ChangeEventHandler } from 'react';
import { useState } from 'react';

const useReviewTextarea = () => {
  const [content, setContent] = useState('');

  const handleReviewInput: ChangeEventHandler<HTMLTextAreaElement> = (event) => {
    setContent(event.currentTarget.value);
  };

  return { content, setContent, handleReviewInput };
};

export default useReviewTextarea;
