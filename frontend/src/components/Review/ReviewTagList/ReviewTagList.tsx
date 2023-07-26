import { Button, Heading } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import ReviewTagItem from '../ReviewTagItem/ReviewTagItem';

import { SvgIcon } from '@/components/Common';
import reviewTagList from '@/mocks/data/reviewTagList.json';

const MAX_DISPLAYED_TAGS = 8;
const MAX_SELECTED_TAGS = 3;

const ReviewTagList = () => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MAX_DISPLAYED_TAGS);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const canShowMore = reviewTagList.length > maxDisplayedTags;
  const displayedTagList = reviewTagList.slice(0, maxDisplayedTags);

  const showMoreTags = () => {
    setMaxDisplayedTags(maxDisplayedTags + MAX_DISPLAYED_TAGS);
  };

  const toggleTagSelection = (id: number, isSelected: boolean) => {
    if (selectedTags.length >= MAX_SELECTED_TAGS && !isSelected) {
      return;
    }

    setSelectedTags((prevSelectedTags) => {
      if (isSelected) {
        return prevSelectedTags.filter((selectedTag) => selectedTag !== id);
      }
      return [...prevSelectedTags, id];
    });
  };

  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="xl">
        태그를 골라주세요. (3개)
        <RequiredMark>*</RequiredMark>
      </Heading>
      <TagListWrapper>
        {displayedTagList.map(({ id, content }) => {
          const isSelected = selectedTags.includes(id);
          const isDisabled = selectedTags.length >= MAX_SELECTED_TAGS && !isSelected;
          return (
            <TagItemWrapper key={id}>
              <ReviewTagItem
                id={id}
                content={content}
                isSelected={isSelected}
                isDisabled={isDisabled}
                toggleTagSelection={toggleTagSelection}
              />
            </TagItemWrapper>
          );
        })}
      </TagListWrapper>
      {canShowMore && (
        <Button color="white" variant="filled" onClick={showMoreTags}>
          <SvgIcon variant="arrow" width={15} css="transform: rotate(270deg)" />
        </Button>
      )}
    </ReviewTagListContainer>
  );
};

export default ReviewTagList;

const ReviewTagListContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
`;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const TagListWrapper = styled.ul`
  text-align: center;
`;

const TagItemWrapper = styled.li`
  display: inline-block;
  margin: 12px 8px;
`;
