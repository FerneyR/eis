const Tittle = ({ children }: { children: string }) => {
  return (
    <p className="focus:outline-none text-base sm:text-lg md:text-xl lg:text-2xl font-bold leading-normal text-indigo-700">
      {children}
    </p>
  );
};

export default Tittle;
