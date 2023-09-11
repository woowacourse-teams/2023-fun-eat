import { Button, Heading, Spacing } from '@fun-eat/design-system';
import type { ChangeEventHandler, FormEventHandler } from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

import { Input, SectionTitle, SvgIcon } from '@/components/Common';
import { IMAGE_MAX_SIZE } from '@/constants';
import { useImageUploader } from '@/hooks/common';
import { useMemberModifyMutation, useMemberQuery } from '@/hooks/queries/members';
import { useS3Upload } from '@/hooks/s3';

const MemberModifyPage = () => {
  const { data: member } = useMemberQuery();
  const { mutateAsync } = useMemberModifyMutation();

  const { previewImage, imageFile, uploadImage } = useImageUploader();
  const { uploadToS3, fileUrl } = useS3Upload(imageFile);

  const [nickname, setNickname] = useState(member?.nickname ?? '');
  const navigate = useNavigate();

  if (!member) {
    return null;
  }

  const modifyNickname: ChangeEventHandler<HTMLInputElement> = (event) => {
    setNickname(event.target.value);
  };

  const handleImageUpload: ChangeEventHandler<HTMLInputElement> = (event) => {
    if (!event.target.files) {
      return;
    }

    const imageFile = event.target.files[0];

    if (imageFile.size > IMAGE_MAX_SIZE) {
      alert('이미지 크기가 너무 커요. 5MB 이하의 이미지를 골라주세요.');
      event.target.value = '';
      return;
    }

    uploadImage(imageFile);
  };

  const handleSubmit: FormEventHandler<HTMLFormElement> = async (event) => {
    event.preventDefault();

    if (imageFile === null || fileUrl === null) {
      return;
    }

    try {
      await uploadToS3();
      await mutateAsync(
        { nickname, image: fileUrl },
        {
          onSuccess: () => {
            navigate('/members');
          },
        }
      );
    } catch (error) {
      if (error instanceof Error) {
        alert(error.message);
        return;
      }

      alert('회원정보 수정을 다시 시도해주세요.');
    }
  };

  return (
    <>
      <SectionTitle name="" />
      <MemberForm onSubmit={handleSubmit}>
        <div>
          <MemberImageUploaderContainer>
            <MemberImageUploaderWrapper>
              <UserProfileImageWrapper>
                <ProfileImage src={previewImage ? previewImage : member.profileImage} alt="업로드한 사진" width={80} />
              </UserProfileImageWrapper>
              <UserImageUploaderLabel>
                <input type="file" accept="image/*" onChange={handleImageUpload} />
                <SvgIcon variant="camera" width={20} height={20} />
              </UserImageUploaderLabel>
            </MemberImageUploaderWrapper>
          </MemberImageUploaderContainer>
          <Spacing size={44} />
          <Heading as="h2" size="xl" tabIndex={0}>
            닉네임
          </Heading>
          <Spacing size={12} />
          <Input value={nickname} customWidth="100%" onChange={modifyNickname} />
        </div>
        <FormButton customWidth="100%" customHeight="60px" size="xl" weight="bold">
          수정하기
        </FormButton>
      </MemberForm>
    </>
  );
};

export default MemberModifyPage;

const MemberImageUploaderContainer = styled.div`
  display: flex;
  justify-content: center;
`;

const MemberImageUploaderWrapper = styled.div`
  position: relative;
`;

const UserProfileImageWrapper = styled.div`
  width: 80px;
  height: 80px;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  background: ${({ theme }) => theme.backgroundColors.default};
  overflow: hidden;
`;

const ProfileImage = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover;
`;

const UserImageUploaderLabel = styled.label`
  position: absolute;
  top: 54px;
  right: -5px;
  width: 30px;
  height: 30px;
  text-align: center;
  border: 1px solid ${({ theme }) => theme.borderColors.disabled};
  border-radius: 50%;
  background: ${({ theme }) => theme.backgroundColors.default};
  cursor: pointer;

  & > input {
    display: none;
  }

  & > svg {
    transform: translateX(-0.5px) translateY(3px);
  }
`;

const MemberForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 92%;
`;

const FormButton = styled(Button)`
  color: ${({ theme }) => theme.colors.black};
  background: ${({ theme }) => theme.colors.primary};
`;
