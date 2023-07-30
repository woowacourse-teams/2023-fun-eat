import { useState } from 'react';

import type { ReviewTag } from '@/types/review';

const getMaxTagsInGroup = (tagList: ReviewTag[]) => {
  return tagList.reduce((max, { tags }) => Math.max(max, tags.length), 0);
};

const useReviewTag = (initialReviewTagList: ReviewTag[], MIN_DISPLAYED_TAGS: number) => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MIN_DISPLAYED_TAGS);

  const canShowMore = maxDisplayedTags < getMaxTagsInGroup(initialReviewTagList);

  const showMoreTags = () => {
    setMaxDisplayedTags(getMaxTagsInGroup(initialReviewTagList));
  };

  return { maxDisplayedTags, canShowMore, showMoreTags };
};

export default useReviewTag;
