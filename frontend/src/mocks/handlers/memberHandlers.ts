import { rest } from 'msw';

export const memberHandlers = [
  rest.get('/api/members', (req, res, ctx) => {
    const { sessionId } = req.cookies;

    if (sessionId === 'abc') {
      return res(ctx.status(200), ctx.json({ nickname: '야미', profileImage: '' }));
    }

    if (sessionId === 'qwe') {
      return res(ctx.status(200), ctx.json({ nickname: '뇨미', profileImage: '' }));
    }

    return res(ctx.status(403));
  }),
];
