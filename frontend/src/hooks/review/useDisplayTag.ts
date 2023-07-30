import { useState } from 'react';

import type { ReviewTag } from '@/types/review';

const getMaxTagsInGroup = (tagList: ReviewTag[]) => {
  return tagList.reduce((max, { tags }) => Math.max(max, tags.length), 0);
};

const useDisplayTag = (initialReviewTagList: ReviewTag[], minDisplayedTagsLength: number) => {
  const [minDisplayedTags, setMinDisplayedTags] = useState(minDisplayedTagsLength);

  const canShowMore = minDisplayedTags < getMaxTagsInGroup(initialReviewTagList);

  const showMoreTags = () => {
    setMinDisplayedTags(getMaxTagsInGroup(initialReviewTagList));
  };

  return { minDisplayedTags, canShowMore, showMoreTags };
};

export default useDisplayTag;
