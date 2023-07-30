import { useState } from 'react';

import type { ReviewTag } from '@/types/review';

const MAX_DISPLAYED_TAGS = 3;

const getMaxTagsInGroup = (tagList: ReviewTag[]) => {
  return tagList.reduce((max, { tags }) => Math.max(max, tags.length), 0);
};

const useReviewTag = (initialReviewTagList: ReviewTag[]) => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MAX_DISPLAYED_TAGS);

  const canShowMore = maxDisplayedTags < getMaxTagsInGroup(initialReviewTagList);

  const showMoreTags = () => {
    setMaxDisplayedTags(getMaxTagsInGroup(initialReviewTagList));
  };

  return { MAX_DISPLAYED_TAGS, maxDisplayedTags, canShowMore, showMoreTags };
};

export default useReviewTag;
