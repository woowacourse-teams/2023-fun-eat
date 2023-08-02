import { Button, Heading, Spacing } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import ReviewTagItem from '../ReviewTagItem/ReviewTagItem';

import { SvgIcon } from '@/components/Common';
import { TAG_TITLE } from '@/constants';
import { useDisplayTag, useReviewTags } from '@/hooks/review';

const MIN_DISPLAYED_TAGS_LENGTH = 3;

const ReviewTagList = () => {
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const { data: tagsData } = useReviewTags();
  const { minDisplayedTags, canShowMore, showMoreTags } = useDisplayTag(tagsData ?? [], MIN_DISPLAYED_TAGS_LENGTH);

  if (!tagsData) {
    return null;
  }

  const toggleTagSelection = (id: number, isSelected: boolean) => {
    if (selectedTags.length >= MIN_DISPLAYED_TAGS_LENGTH && !isSelected) {
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
        {tagsData.map(({ tagType, tags }) => {
          return (
            <TagItemWrapper key={tagType}>
              <TagTitle as="h3" size="md">
                {TAG_TITLE[tagType]}
              </TagTitle>
              <Spacing size={20} />
              <ul>
                {tags.slice(0, minDisplayedTags).map(({ id, name }) => (
                  <li key={id}>
                    <ReviewTagItem
                      id={id}
                      name={name}
                      isSelected={selectedTags.includes(id)}
                      toggleTagSelection={toggleTagSelection}
                    />
                    <Spacing size={5} />
                  </li>
                ))}
              </ul>
            </TagItemWrapper>
          );
        })}
      </TagListWrapper>
      <Spacing size={26} />
      {canShowMore && (
        <Button
          type="button"
          customHeight="fit-content"
          variant="transparent"
          onClick={showMoreTags}
          aria-label="태그 더보기"
        >
          <SvgWrapper>
            <SvgIcon variant="arrow" width={15} />
          </SvgWrapper>
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
  column-gap: 20px;
  width: 100%;
  margin: 0 auto;
  overflow-x: auto;

  &::-webkit-scrollbar {
    display: none;
  }

  & > div {
    flex-grow: 1;
  }

  @media screen and (min-width: 420px) {
    justify-content: center;

    & > div {
      flex-grow: 0;
    }
  }
`;

const TagItemWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

const TagTitle = styled(Heading)`
  text-align: center;
`;

const SvgWrapper = styled.div`
  transform: rotate(270deg);
`;
