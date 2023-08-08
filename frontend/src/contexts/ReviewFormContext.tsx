import type { PropsWithChildren } from 'react';
import { createContext, useState } from 'react';

import { MIN_DISPLAYED_TAGS_LENGTH } from '@/constants';

interface ReviewFormValue {
  rating: number;
  tagIds: number[];
  content: string;
  rebuy: boolean;
}

type ReviewFormKey = keyof ReviewFormValue;

interface ReviewFormActionParams {
  target: ReviewFormKey;
  value: string | number | boolean;
  isSelected?: boolean;
}

type ReviewFormAction = (params: ReviewFormActionParams) => void;

const initialReviewFormValue: ReviewFormValue = {
  rating: 0,
  tagIds: [],
  content: '',
  rebuy: false,
};

export const ReviewFormValueContext = createContext<ReviewFormValue | null>(null);
export const ReviewFormActionContext = createContext<ReviewFormAction | null>(null);

const ReviewFormProvider = ({ children }: PropsWithChildren) => {
  const [reviewFormValue, setReviewFormValue] = useState(initialReviewFormValue);

  const handleReviewFormValue = ({ target, value, isSelected }: ReviewFormActionParams) => {
    setReviewFormValue((prev) => {
      const targetValue = prev[target];

      if (typeof targetValue === 'boolean') {
        return { ...prev, [target]: !targetValue };
      }

      if (Array.isArray(targetValue)) {
        if (targetValue.length >= MIN_DISPLAYED_TAGS_LENGTH && !isSelected) {
          return prev;
        }

        if (isSelected) {
          return { ...prev, [target]: targetValue.filter((tagId) => tagId !== value) };
        }

        return { ...prev, [target]: [...targetValue, value] };
      }

      return { ...prev, [target]: value };
    });
  };

  return (
    <ReviewFormActionContext.Provider value={handleReviewFormValue}>
      <ReviewFormValueContext.Provider value={reviewFormValue}>{children}</ReviewFormValueContext.Provider>
    </ReviewFormActionContext.Provider>
  );
};

export default ReviewFormProvider;
