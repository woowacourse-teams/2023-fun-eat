import { useToastActionContext } from '@fun-eat/design-system';
import type { PropsWithChildren } from 'react';
import { createContext, useState } from 'react';

import { MIN_DISPLAYED_TAGS_LENGTH } from '@/constants';
import type { ReviewRequest, ReviewRequestKey } from '@/types/review';

interface ReviewFormActionParams {
  target: ReviewRequestKey;
  value: string | number | boolean;
  isSelected?: boolean;
}

interface ReviewFormAction {
  handleReviewFormValue: (params: ReviewFormActionParams) => void;
  resetReviewFormValue: () => void;
}

const initialReviewFormValue: ReviewRequest = {
  rating: 0,
  tagIds: [],
  content: '',
  rebuy: false,
};

export const ReviewFormValueContext = createContext<ReviewRequest | null>(null);
export const ReviewFormActionContext = createContext<ReviewFormAction | null>(null);

const ReviewFormProvider = ({ children }: PropsWithChildren) => {
  const [reviewFormValue, setReviewFormValue] = useState(initialReviewFormValue);
  const { toast } = useToastActionContext();

  const handleReviewFormValue = ({ target, value, isSelected }: ReviewFormActionParams) => {
    setReviewFormValue((prev) => {
      const targetValue = prev[target];

      if (Array.isArray(targetValue)) {
        if (targetValue.length >= MIN_DISPLAYED_TAGS_LENGTH && !isSelected) {
          toast.success(`태그는 ${MIN_DISPLAYED_TAGS_LENGTH}까지 선택할 수 있습니다`);
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

  const resetReviewFormValue = () => {
    setReviewFormValue(initialReviewFormValue);
  };

  const reviewFormAction = {
    handleReviewFormValue,
    resetReviewFormValue,
  };

  return (
    <ReviewFormActionContext.Provider value={reviewFormAction}>
      <ReviewFormValueContext.Provider value={reviewFormValue}>{children}</ReviewFormValueContext.Provider>
    </ReviewFormActionContext.Provider>
  );
};

export default ReviewFormProvider;
