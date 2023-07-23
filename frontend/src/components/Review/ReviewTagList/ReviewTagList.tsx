import { Badge, Heading, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import reviewTagList from '@/mocks/data/reviewTagList.json';

const MAX_DISPLAYED_TAGS = 8;
const MAX_SELECTED_TAGS = 3;

const ReviewTagList = () => {
  const [maxDisplayedTags, setMaxDisplayedTags] = useState(MAX_DISPLAYED_TAGS);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);

  const showMoreTags = () => {
    setMaxDisplayedTags(maxDisplayedTags + MAX_DISPLAYED_TAGS);
  };

  const toggleTagSelection = (id: number) => {
    setSelectedTags((prevSelectedTags) => {
      const isSelected = prevSelectedTags.includes(id);
      if (isSelected) {
        return prevSelectedTags.filter((selectedTag) => selectedTag !== id);
      }
      return [...prevSelectedTags, id];
    });
  };

  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="lg">
        상품에 관한 태그를 선택해주세요 (3개)
      </Heading>
      <TagListWrapper>
        {reviewTagList.slice(0, maxDisplayedTags).map(({ id, content }) => {
          const isSelected = selectedTags.includes(id);
          return (
            // TODO: 태그 색 매핑
            <TagItem key={id} onClick={() => toggleTagSelection(id)}>
              <Badge
                color={selectedTags.length < MAX_SELECTED_TAGS ? theme.colors.primary : theme.colors.gray2}
                textColor={theme.textColors.default}
                css={isSelected && `border: 1px solid ${theme.colors.information}`}
              >
                {content}
              </Badge>
            </TagItem>
          );
        })}
      </TagListWrapper>
      {reviewTagList.length > maxDisplayedTags && (
        <button onClick={showMoreTags}>
          <SvgIcon
            variant="arrow"
            width={15}
            css={`
              transform: rotate(270deg);
            `}
          />
        </button>
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

const TagListWrapper = styled.ul`
  text-align: center;
`;

const TagItem = styled.li`
  display: inline-block;
  margin: 4px 8px;
`;
