
-- Column: etat

-- ALTER TABLE public.menu DROP COLUMN etat;

ALTER TABLE public.menu ADD COLUMN etat boolean;
ALTER TABLE public.menu ALTER COLUMN etat SET DEFAULT true;
