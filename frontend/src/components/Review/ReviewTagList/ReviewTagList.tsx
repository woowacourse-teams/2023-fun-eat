import { Button, Heading, Spacing } from '@fun-eat/design-system';
import styled from 'styled-components';

import ReviewTagItem from '../ReviewTagItem/ReviewTagItem';

import { SvgIcon } from '@/components/Common';
import { MIN_DISPLAYED_TAGS_LENGTH, TAG_TITLE } from '@/constants';
import { useReviewTagsQuery } from '@/hooks/queries/review';
import { useDisplayTag } from '@/hooks/review';

interface ReviewTagListProps {
  selectedTags: number[];
}

const ReviewTagList = ({ selectedTags }: ReviewTagListProps) => {
  const { data: tagsData } = useReviewTagsQuery();
  const { minDisplayedTags, canShowMore, showMoreTags } = useDisplayTag(tagsData, MIN_DISPLAYED_TAGS_LENGTH);

  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="xl" tabIndex={0}>
        태그를 골라주세요. (3개까지)
        <RequiredMark aria-label="필수 작성">*</RequiredMark>
      </Heading>
      <Spacing size={25} />
      <TagListWrapper>
        {tagsData.map(({ tagType, tags }) => (
          <TagItemWrapper key={tagType}>
            <TagTitle as="h3" size="md" tabIndex={0}>
              {TAG_TITLE[tagType]}
            </TagTitle>
            <Spacing size={20} />
            <ul>
              {tags.slice(0, minDisplayedTags).map(({ id, name }) => (
                <>
                  <li key={id}>
                    <ReviewTagItem id={id} name={name} variant={tagType} isSelected={selectedTags.includes(id)} />
                  </li>
                  <Spacing size={5} />
                </>
              ))}
            </ul>
          </TagItemWrapper>
        ))}
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
  width: 100%;
  margin: 0 auto;
  column-gap: 20px;
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
  align-items: center;
  min-width: 100px;
`;

const TagTitle = styled(Heading)`
  text-align: center;
`;

const SvgWrapper = styled.span`
  display: inline-block;
  transform: rotate(270deg);
`;
