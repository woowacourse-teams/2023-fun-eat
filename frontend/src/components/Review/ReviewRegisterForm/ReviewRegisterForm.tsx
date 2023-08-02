import { Button, Checkbox, Divider, Heading, Spacing, theme } from '@fun-eat/design-system';
import { useEffect, useState } from 'react';
import styled from 'styled-components';

import ReviewImageUploader from '../ReviewImageUploader/ReviewImageUploader';
import ReviewTagList from '../ReviewTagList/ReviewTagList';
import ReviewTextarea from '../ReviewTextarea/ReviewTextarea';
import StarRate from '../StarRate/StarRate';

import { SvgIcon } from '@/components/Common';
import { ProductOverviewItem } from '@/components/Product';
import { MIN_DISPLAYED_TAGS_LENGTH } from '@/constants';
import { useReviewTextarea, useSelectedTags } from '@/hooks/review';
import useReviewImageUploader from '@/hooks/review/useReviewImageUploader';
import useReviewRegisterForm from '@/hooks/review/useReviewRegisterForm';
import useStarRating from '@/hooks/useStarRate';
import type { ProductDetail } from '@/types/product';

const MIN_RATING_SCORE = 0;
const MIN_SELECTED_TAGS_COUNT = 1;
const MIN_CONTENT_LENGTH = 1;

interface ReviewRegisterFormProps {
  product: ProductDetail;
  close: () => void;
}

const ReviewRegisterForm = ({ product, close }: ReviewRegisterFormProps) => {
  const { reviewImage, uploadReviewImage, deleteReviewImage } = useReviewImageUploader();
  const { rating, handleRating } = useStarRating();
  const { selectedTags, toggleTagSelection } = useSelectedTags(MIN_DISPLAYED_TAGS_LENGTH);
  const { content, handleReviewInput } = useReviewTextarea();
  const [rebuy, setRebuy] = useState(false);
  const [submitEnabled, setSubmitEnabled] = useState(false);

  const { request } = useReviewRegisterForm(product.id);

  const handleRebuy = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRebuy(event.target.checked);
  };

  // const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
  //   event.preventDefault();

  //   const formData = new FormData();

  //   // if (reviewImage) {
  //   //   formData.append('image', reviewImage);
  //   // }

  //   const reviewRequest = JSON.stringify({
  //     rating: rating,
  //     tagIds: selectedTags,
  //     content: content,
  //     rebuy: rebuy,
  //   });

  //   const jsonBlob = new Blob([reviewRequest], { type: 'application/json' });
  //   formData.append('reviewRequest', jsonBlob);

  //   const url = `http://3.36.100.213/api/products/${product.id}/reviews`;

  //   const headers = {
  //     'Content-Type': 'multipart/form-data',
  //   };

  //   const response = await fetch(url, {
  //     method: 'POST',
  //     headers: headers,
  //     body: formData,
  //   });

  //   if (!response.ok) {
  //     throw new Error(`에러 발생 상태코드:${response.status}`);
  //   }

  //   console.log('리뷰가 성공적으로 등록되었습니다.');

  //   // 다음 작업을 수행...

  //   // await request(formData);
  // };

  // const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
  //   event.preventDefault();

  //   const formData = new FormData();

  //   if (reviewImage) {
  //     formData.append('image', reviewImage);
  //   }

  //   // JSON 리뷰 데이터에 이러한 속성들을 추가
  //   const reviewRequest = {
  //     rating,
  //     tagIds: selectedTags,
  //     content,
  //     rebuy,
  //   };

  //   // JSON 리뷰 데이터를 문자열로 변환
  //   const jsonString = JSON.stringify(reviewRequest);

  //   // 문자열을 Blob 객체로 변환. mimeType은 'application/json'으로 설정
  //   const jsonBlob = new Blob([jsonString], { type: 'application/json' });

  //   // 리뷰 요청으로 JSON Blob 객체를 추가
  //   formData.append('reviewRequest', jsonBlob);

  //   await request(formData);
  // };

  const handleSubmit: React.FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    const formData = new FormData();

    formData.append('image', reviewImage ?? null);
    formData.append(
      'reviewRequest',
      JSON.stringify({
        rating,
        tagIds: selectedTags,
        content,
        rebuy,
      })
    );

    const url = `https://funeat.site/api/products/${product.id}/reviews`;

    const headers = {
      'Content-Type': 'multipart/form-data',
    };

    const response = await fetch(url, {
      method: 'POST',
      headers: headers,
      body: formData,
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error(`에러 발생 상태코드:${response.status}`);
    }

    console.log(response, '리뷰가 성공적으로 등록되었습니다.');

    // await request(formData);
  };

  // useEffect(() => {
  //   const isValid =
  //     rating > MIN_RATING_SCORE &&
  //     selectedTags.length === MIN_SELECTED_TAGS_COUNT &&
  //     content.length > MIN_CONTENT_LENGTH;
  //   setSubmitEnabled(isValid);
  // }, [rating, selectedTags, content]);

  return (
    <ReviewRegisterFormContainer>
      <ReviewHeading>리뷰 작성</ReviewHeading>
      <CloseButton variant="transparent" onClick={close} aria-label="닫기">
        <SvgIcon variant="close" color={theme.colors.black} width={20} height={20} />
      </CloseButton>
      <Divider />
      <ProductOverviewItemWrapper>
        <ProductOverviewItem name={product.name} image={product.image} />
      </ProductOverviewItemWrapper>
      <Divider variant="disabled" css="height:4px;" />
      <RegisterForm onSubmit={handleSubmit}>
        <ReviewImageUploader
          reviewImage={reviewImage}
          uploadReviewImage={uploadReviewImage}
          deleteReviewImage={deleteReviewImage}
        />
        <Spacing size={60} />
        <StarRate rating={rating} handleRating={handleRating} />
        <Spacing size={60} />
        <ReviewTagList selectedTags={selectedTags} toggleTagSelection={toggleTagSelection} />
        <Spacing size={60} />
        <ReviewTextarea content={content} onReviewInput={handleReviewInput} />
        <Spacing size={80} />
        <Checkbox weight="bold" onChange={handleRebuy}>
          재구매할 생각이 있으신가요?
        </Checkbox>
        <Spacing size={16} />
        <FormButton
          type="submit"
          customWidth="100%"
          customHeight="60px"
          size="xl"
          weight="bold"
          // disabled={!submitEnabled}
        >
          등록하기
        </FormButton>
      </RegisterForm>
    </ReviewRegisterFormContainer>
  );
};

export default ReviewRegisterForm;

const ReviewRegisterFormContainer = styled.div`
  position: relative;
  height: 100%;
`;

const ReviewHeading = styled(Heading)`
  height: 80px;
  text-align: center;
  font-size: 2.4rem;
  line-height: 80px;
`;

const CloseButton = styled(Button)`
  position: absolute;
  top: 24px;
  right: 32px;
`;

const ProductOverviewItemWrapper = styled.div`
  margin: 15px 0;
`;

const RegisterForm = styled.form`
  padding: 50px 20px;
`;

const FormButton = styled(Button)`
  background: ${({ theme, disabled }) => (disabled ? theme.colors.grey : theme.colors.primary)};
  color: ${({ theme, disabled }) => (disabled ? theme.colors.white : theme.colors.black)};
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};
`;
