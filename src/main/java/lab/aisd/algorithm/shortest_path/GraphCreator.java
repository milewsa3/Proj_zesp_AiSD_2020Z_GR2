package lab.aisd.algorithm.shortest_path;

import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.model.Vertex;
import lab.aisd.model.Hospital;
import lab.aisd.model.Path;
import lab.aisd.util.input.InputData;

import java.util.ArrayList;
import java.util.List;

public class GraphCreator {
    public Graph createGraph(InputData inputData){
        List<Vertex> vertices = new ArrayList<>();
        List<Path> paths = inputData.getPaths();
        List<Hospital> hosps = inputData.getHospitals();
        
        Graph graph = new Graph(hosps.size());
        int index = 0;
        
        for(Hospital hosp : hosps){
            boolean isHospital = true;
            if("INTERSECTION".equals(hosp.getName())){
                isHospital = false;
            }
            vertices.add(new Vertex(hosp, isHospital, index));
            index++;
        }
        
        for(Path path : paths) {
            int firstID = path.getFirstHospitalID() - 1;
            int secondID = path.getSecondHospitalID() - 1;
            int distance = path.getDistance();
            
            if(firstID < 0 || secondID < 0){
                //path replaced by intersection
                continue;
            }
            graph.addEdge(vertices.get(firstID), vertices.get(secondID), distance);
        }
        
        return graph;
    }
}
