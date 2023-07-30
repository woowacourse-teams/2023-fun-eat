import { useState } from 'react';

import type { ReviewTag } from '@/types/review';

const getMaxTagsInGroup = (tagList: ReviewTag[]) => {
  return tagList.reduce((max, { tags }) => Math.max(max, tags.length), 0);
};

const useDisplayTag = (initialReviewTagList: ReviewTag[], minDisplayedTags: number) => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(minDisplayedTags);

  const canShowMore = maxDisplayedTags < getMaxTagsInGroup(initialReviewTagList);

  const showMoreTags = () => {
    setMaxDisplayedTags(getMaxTagsInGroup(initialReviewTagList));
  };

  return { maxDisplayedTags, canShowMore, showMoreTags };
};

export default useDisplayTag;
