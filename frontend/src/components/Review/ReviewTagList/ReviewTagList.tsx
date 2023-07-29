import { Button, Heading, Spacing } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import ReviewTagItem from '../ReviewTagItem/ReviewTagItem';

import { SvgIcon } from '@/components/Common';
import { TAG_TITLE } from '@/constants';
import reviewTagList from '@/mocks/data/reviewTagList.json';
import { isTagNameVariant, type TagNameOption } from '@/types/common';

const MAX_DISPLAYED_TAGS = 3;

const ReviewTagList = () => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MAX_DISPLAYED_TAGS);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const convertTagName = (tagName: TagNameOption): string => TAG_TITLE[tagName] || null;

  const getMaxTagsInGroup = (tagList: typeof reviewTagList) => {
    return tagList.reduce((max, currentGroup) => {
      return Math.max(max, currentGroup.tags.length);
    }, 0);
  };

  const canShowMore = maxDisplayedTags < getMaxTagsInGroup(reviewTagList);

  const showMoreTags = () => {
    setMaxDisplayedTags(getMaxTagsInGroup(reviewTagList));
  };

  const toggleTagSelection = (id: number) => {
    const isSelected = selectedTags.includes(id);

    if (selectedTags.length >= MAX_DISPLAYED_TAGS && !isSelected) {
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
      <Spacing size={25} />
      <TagListWrapper>
        {reviewTagList.map((tagGroup) => {
          if (!isTagNameVariant(tagGroup.tagType)) {
            return;
          }
          return (
            <ul key={tagGroup.tagType}>
              <TagTitle as="h3" size="md">
                {convertTagName(tagGroup.tagType)}
              </TagTitle>
              <Spacing size={20} />
              {tagGroup.tags.slice(0, maxDisplayedTags).map((tag) => (
                <li key={tag.id}>
                  <ReviewTagItem
                    id={tag.id}
                    name={tag.name}
                    isSelected={selectedTags.includes(tag.id)}
                    toggleTagSelection={toggleTagSelection}
                  />
                  <Spacing size={5} />
                </li>
              ))}
            </ul>
          );
        })}
      </TagListWrapper>
      <Spacing size={26} />
      {canShowMore && (
        <Button type="button" customHeight="fit-content" variant="transparent" onClick={showMoreTags}>
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
`;

const RequiredMark = styled.sup`
  color: ${({ theme }) => theme.colors.error};
`;

const TagListWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  column-gap: 30px;
  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }

  @media screen and (max-width: 600px) {
    justify-content: normal;
  }
`;

const TagTitle = styled(Heading)`
  text-align: center;
`;
