import { Badge, Heading, theme } from '@fun-eat/design-system';
import { useState } from 'react';
import styled from 'styled-components';

import { SvgIcon } from '@/components/Common';
import reviewTagList from '@/mocks/data/reviewTagList.json';

const ReviewTagList = () => {
  const DISPLAYED_TAGS_LIMIT = 8;
  const [displayedTagsLimit, setDisplayedTagsLimit] = useState(DISPLAYED_TAGS_LIMIT);

  const showMoreTags = () => {
    setDisplayedTagsLimit(displayedTagsLimit + DISPLAYED_TAGS_LIMIT);
  };

  return (
    <ReviewTagListContainer>
      <Heading as="h2" size="lg">
        상품에 관한 태그를 선택해주세요 (3개)
      </Heading>
      <TagListWrapper>
        {reviewTagList.slice(0, displayedTagsLimit).map(({ id, content }) => (
          <TagItem key={id}>
            <Badge color={theme.colors.primary} textColor={theme.textColors.default}>
              {content}
            </Badge>
          </TagItem>
        ))}
      </TagListWrapper>
      {reviewTagList.length > displayedTagsLimit && (
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
