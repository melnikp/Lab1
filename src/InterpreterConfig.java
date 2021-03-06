import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InterpreterConfig  extends Interpreter {
    public enum Params {INPUT_FILE, OUTPUT_FILE, PARAM_FILE}

    public static final Map<String, Enum> lexemeMap;

    static
    {
        lexemeMap = new HashMap<>();
        lexemeMap.put("IN", Params.INPUT_FILE);
        lexemeMap.put("OUT", Params.OUTPUT_FILE);
        lexemeMap.put("PARAM", Params.PARAM_FILE);

    }

    public static void Interpreted(String config, Map<Enum, String> resultMap) throws IOException
    {
        MyReader reader = new MyReader(config);
        String ParamString;

        while ((ParamString = reader.ReadLine()) != null)
        {
            if ((ParamString = ParamString.replaceAll("\\s", "")).isEmpty())
            {
                continue;
            }
            String[] ParamPair = ParamString.split(COLON);
            if (!IsPairCorrect(ParamPair, lexemeMap))
            {
                throw new IOException();
            }
            resultMap.put(lexemeMap.get(ParamPair[0]), ParamPair[1]);
        }

        if (!resultMap.containsKey(Params.INPUT_FILE))
        {
            Log.report("Input file is not found");
            throw new FileNotFoundException();
        }

        if (!resultMap.containsKey(Params.PARAM_FILE))
        {
            Log.report("Param file is not found");
            throw new FileNotFoundException();
        }

        if (resultMap.putIfAbsent(Params.OUTPUT_FILE, "output_default.txt") == null)
        {
            Log.report("Output file is not found, using output_default.txt file");
        }

        reader.CloseStream();
    }

}
