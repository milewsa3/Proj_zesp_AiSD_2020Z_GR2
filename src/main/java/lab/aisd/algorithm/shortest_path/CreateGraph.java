package lab.aisd.algorithm.shortest_path;

import java.util.ArrayList;
import java.util.List;
import lab.aisd.algorithm.model.*;
import lab.aisd.util.input.InputData;
import lab.aisd.model.*;

public class CreateGraph {
    public Graph createGraph(InputData inputData){
        List<Node> nodes = new ArrayList<>();
        List<Path> paths = inputData.getPaths();
        List<Hospital> hosps = inputData.getHospitals();
        
        Graph graph = new Graph(hosps.size());
        int index = 0;
        
        for(Hospital hosp : hosps){
            boolean isHospital = true;
            if("INTERSECTION".equals(hosp.getName())){
                isHospital = false;
            }
            nodes.add(new Node(hosp, isHospital, index));
            index++;
        }
        
        for(Path path : paths) {
            int firstID = path.getFirstHospitalID() - 1;
            int secondID = path.getSecondHospitalID() - 1;
            int distance = path.getDistance();
            
            graph.addEdge(nodes.get(firstID), nodes.get(secondID), distance);
        }
        
        return graph;
    }
}
